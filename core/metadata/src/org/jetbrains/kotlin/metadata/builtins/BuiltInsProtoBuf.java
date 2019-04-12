/*
 * Copyright 2010-2019 JetBrains s.r.o. and Kotlin Project contributors. Use of this source code is governed
 * by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: core/metadata/src/builtins.proto

package org.jetbrains.kotlin.metadata.builtins;

public final class BuiltInsProtoBuf {
  private BuiltInsProtoBuf() {}
  public static void registerAllExtensions(
      org.jetbrains.kotlin.protobuf.ExtensionRegistryLite registry) {
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.packageFqName);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.classAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.constructorAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.functionAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.propertyAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.propertyGetterAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.propertySetterAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.compileTimeValue);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.enumEntryAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.parameterAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.typeAnnotation);
    registry.add(org.jetbrains.kotlin.metadata.builtins.BuiltInsProtoBuf.typeParameterAnnotation);
  }
  public static final int PACKAGE_FQ_NAME_FIELD_NUMBER = 151;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Package { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Package,
      java.lang.Integer> packageFqName = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newSingularGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Package.getDefaultInstance(),
        0,
        null,
        null,
        151,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.INT32,
        java.lang.Integer.class);
  public static final int CLASS_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Class { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Class,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> classAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Class.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int CONSTRUCTOR_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Constructor { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Constructor,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> constructorAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Constructor.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int FUNCTION_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Function { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Function,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> functionAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Function.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int PROPERTY_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Property { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Property,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> propertyAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Property.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int PROPERTY_GETTER_ANNOTATION_FIELD_NUMBER = 152;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Property { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Property,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> propertyGetterAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Property.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        152,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int PROPERTY_SETTER_ANNOTATION_FIELD_NUMBER = 153;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Property { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Property,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> propertySetterAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Property.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        153,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int COMPILE_TIME_VALUE_FIELD_NUMBER = 151;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Property { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Property,
      org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.Argument.Value> compileTimeValue = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newSingularGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Property.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.Argument.Value.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.Argument.Value.getDefaultInstance(),
        null,
        151,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.Argument.Value.class);
  public static final int ENUM_ENTRY_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.EnumEntry { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.EnumEntry,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> enumEntryAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.EnumEntry.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int PARAMETER_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.ValueParameter { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.ValueParameter,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> parameterAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.ValueParameter.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int TYPE_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.Type { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.Type,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> typeAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.Type.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);
  public static final int TYPE_PARAMETER_ANNOTATION_FIELD_NUMBER = 150;
  /**
   * <code>extend .org.jetbrains.kotlin.metadata.TypeParameter { ... }</code>
   */
  public static final
    org.jetbrains.kotlin.protobuf.GeneratedMessageLite.GeneratedExtension<
      org.jetbrains.kotlin.metadata.ProtoBuf.TypeParameter,
      java.util.List<org.jetbrains.kotlin.metadata.ProtoBuf.Annotation>> typeParameterAnnotation = org.jetbrains.kotlin.protobuf.GeneratedMessageLite
          .newRepeatedGeneratedExtension(
        org.jetbrains.kotlin.metadata.ProtoBuf.TypeParameter.getDefaultInstance(),
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.getDefaultInstance(),
        null,
        150,
        org.jetbrains.kotlin.protobuf.WireFormat.FieldType.MESSAGE,
        false,
        org.jetbrains.kotlin.metadata.ProtoBuf.Annotation.class);

  static {
  }

  // @@protoc_insertion_point(outer_class_scope)
}