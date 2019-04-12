/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.serialization.js

import org.jetbrains.kotlin.contracts.ContractDeserializerImpl
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.deserialization.PlatformDependentDeclarationFilter
import org.jetbrains.kotlin.descriptors.impl.EmptyPackageFragmentDescriptor
import org.jetbrains.kotlin.incremental.components.LookupTracker
import org.jetbrains.kotlin.metadata.ProtoBuf
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl
import org.jetbrains.kotlin.metadata.js.JsProtoBuf
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.parentOrNull
import org.jetbrains.kotlin.serialization.deserialization.*
import org.jetbrains.kotlin.storage.StorageManager
import org.jetbrains.kotlin.utils.JsMetadataVersion

fun createKotlinJavascriptPackageFragmentProvider(
    storageManager: StorageManager,
    module: ModuleDescriptor,
    header: JsProtoBuf.Header,
    packageFragmentProtos: List<ProtoBuf.PackageFragment>,
    metadataVersion: JsMetadataVersion,
    configuration: DeserializationConfiguration,
    lookupTracker: LookupTracker
): PackageFragmentProvider {
    val packageFragments: MutableList<PackageFragmentDescriptor> = packageFragmentProtos.mapNotNullTo(mutableListOf()) { proto ->
        proto.fqName?.let { fqName ->
            KotlinJavascriptPackageFragment(fqName, storageManager, module, proto, header, metadataVersion, configuration)
        }
    }

    // Generate empty PackageFragmentDescriptor instances for packages that aren't mentioned in compilation units directly.
    // For example, if there's `package foo.bar` directive, we'll get only PackageFragmentDescriptor for `foo.bar`, but
    // none for `foo`. Various descriptor/scope code relies on presence of such package fragments, and currently we
    // don't know if it's possible to fix this.
    // TODO: think about fixing issues in descriptors/scopes
    val packageFqNames = packageFragmentProtos.mapNotNullTo(mutableSetOf()) { it.fqName }
    for (packageFqName in packageFqNames.mapNotNull { it.parentOrNull() }) {
        var ancestorFqName = packageFqName
        while (!ancestorFqName.isRoot && packageFqNames.add(ancestorFqName)) {
            packageFragments += EmptyPackageFragmentDescriptor(module, ancestorFqName)
            ancestorFqName = ancestorFqName.parent()
        }
    }

    val provider = PackageFragmentProviderImpl(packageFragments)

    val notFoundClasses = NotFoundClasses(storageManager, module)

    val components = DeserializationComponents(
        storageManager,
        module,
        configuration,
        DeserializedClassDataFinder(provider),
        AnnotationAndConstantLoaderImpl(module, notFoundClasses, JsSerializerProtocol),
        provider,
        LocalClassifierTypeSettings.Default,
        ErrorReporter.DO_NOTHING,
        lookupTracker,
        DynamicTypeDeserializer,
        emptyList(),
        notFoundClasses,
        ContractDeserializerImpl(configuration, storageManager),
        platformDependentDeclarationFilter = PlatformDependentDeclarationFilter.NoPlatformDependent,
        extensionRegistryLite = JsSerializerProtocol.extensionRegistry
    )

    for (packageFragment in packageFragments.filterIsInstance<KotlinJavascriptPackageFragment>()) {
        packageFragment.initialize(components)
    }

    return provider
}

private val ProtoBuf.PackageFragment.fqName: FqName?
    get() {
        val nameResolver = NameResolverImpl(strings, qualifiedNames)
        return when {
            hasPackage() -> FqName(nameResolver.getPackageFqName(`package`.getExtension(JsProtoBuf.packageFqName)))
            class_Count > 0 -> nameResolver.getClassId(class_OrBuilderList.first().fqName).packageFqName
            else -> null
        }
    }
