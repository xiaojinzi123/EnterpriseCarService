package com.ehi.datasource.compiler;

import com.ehi.datasource.EHiDataSourceAnno;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

/**
 * 实现项目中的 retrofit 接口的代理接口和实现类生成,生成的类作为 datasource 层
 * time   : 2018/08/10
 *
 * @author : xiaojinzi 30212
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"com.ehi.datasource.EHiDataSourceAnno"})
public class EHiDatasourceProcessor extends EHiBaseProcessor {

    private String classNameApi = "com.ehi.datasource.DataSourceApi";
    private String classNameApiImpl = "com.ehi.datasource.DataSourceApiImpl";
    private String classNameApiManager = "com.ehi.datasource.DataSourceManager";

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (CollectionUtils.isNotEmpty(set)) {

            Set<? extends Element> routeElements = roundEnvironment.getElementsAnnotatedWith(EHiDataSourceAnno.class);

            parseAnno(routeElements);

            return true;
        }

        return false;

    }

    /**
     * 解析注解
     *
     * @param currElements
     */
    private void parseAnno(Set<? extends Element> currElements) {

        List<TypeElement> list = new ArrayList<>();

        Set<String> prefixSet = new HashSet<>();
        Set<String> uniqueSet = new HashSet<>();

        for (Element element : currElements) {

            if (!(element instanceof TypeElement)) {

                mMessager.printMessage(Diagnostic.Kind.ERROR, element + " is not a 'TypeElement' ");

                continue;

            }

            // 拿到注解信息
            EHiDataSourceAnno anno = element.getAnnotation(EHiDataSourceAnno.class);

            if (getImplClassName(anno) == null && anno.callPath().isEmpty()) {

                mMessager.printMessage(Diagnostic.Kind.ERROR, element.toString() + ": EHiDataSourceAnno's impl and EHiDataSourceAnno's callPath are both empty");

                continue;
            }

            if (!anno.value().isEmpty()) {

                if (prefixSet.contains(anno.value())) {
                    mMessager.printMessage(Diagnostic.Kind.ERROR, element.toString() + ": EHiDataSourceAnno's value is already exist");
                    continue;
                }
                prefixSet.add(anno.value());

            }

            if (uniqueSet.contains(anno.uniqueCode())) {

                mMessager.printMessage(Diagnostic.Kind.ERROR, element.toString() + ": EHiDataSourceAnno's uniqueCode is not unique");

                continue;
            }

            uniqueSet.add(anno.uniqueCode());

            list.add((TypeElement) element);

        }

        try {
            createDataSourceApi(list);
            createDataSourceApiManager(list);
            createDataSourceApiImpl(list);
        } catch (Exception e) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, "createDataSource fail: " + e.getMessage());
            e.printStackTrace();
        }

    }

    private void createDataSourceApi(List<TypeElement> apiClassList) throws IOException {

        // pkg
        String pkgApi = classNameApi.substring(0, classNameApi.lastIndexOf("."));

        // simpleName
        String cnApi = classNameApi.substring(classNameApi.lastIndexOf(".") + 1);

        TypeSpec.Builder typeSpecBuilder = TypeSpec.interfaceBuilder(cnApi)
                .addModifiers(Modifier.PUBLIC);

        typeSpecBuilder.addJavadoc("所有用注解标记的DataSource都会被整合到这里\n\n");

        for (TypeElement dataSourceApiTypeElement : apiClassList) {

            EHiDataSourceAnno remoteAnno = dataSourceApiTypeElement.getAnnotation(EHiDataSourceAnno.class);

            if (remoteAnno == null) {
                continue;
            }

            typeSpecBuilder.addJavadoc("@see " + dataSourceApiTypeElement.toString() + "\n");
            generateMethods(dataSourceApiTypeElement, typeSpecBuilder, remoteAnno, false);

        }

        TypeSpec typeSpec = typeSpecBuilder.build();

        JavaFile.builder(pkgApi, typeSpec)
                .indent("    ")
                .build()
                .writeTo(mFiler);

    }

    private void createDataSourceApiManager(List<TypeElement> apiClassList) throws IOException {

        // pkg
        String pkgApi = classNameApiManager.substring(0, classNameApiManager.lastIndexOf("."));

        // simpleName
        String cnApi = classNameApiManager.substring(classNameApiManager.lastIndexOf(".") + 1);

        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(cnApi)
                .addModifiers(Modifier.PUBLIC);

        ClassName mapClassName = ClassName.get("java.util", "Map");

        TypeName typeNameMap = ParameterizedTypeName.get(mapClassName, TypeName.get(typeString), TypeName.get(mElements.getTypeElement("java.lang.Object").asType()));

        FieldSpec.Builder mapFieldSpecBuilder = FieldSpec.builder(typeNameMap, "map", Modifier.PRIVATE)
                .initializer("java.util.Collections.synchronizedMap(new java.util.HashMap<String,Object>())");

        typeSpecBuilder
                .addField(mapFieldSpecBuilder.build());

        for (TypeElement dataSourceApiTypeElement : apiClassList) {

            EHiDataSourceAnno anno = dataSourceApiTypeElement.getAnnotation(EHiDataSourceAnno.class);

            String implClassName = getImplClassName(anno);
            if (implClassName == null) {
                continue;
            }

            TypeMirror returnType = dataSourceApiTypeElement.asType();

            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(anno.uniqueCode() + "DataSource")
                    .returns(TypeName.get(returnType));

            mMessager.printMessage(Diagnostic.Kind.NOTE,"tttt====" + implClassName);

            methodSpecBuilder
                    .addStatement(implClassName + " value = (" + implClassName + ") map.get(\"" + anno.uniqueCode() + "\")")
                    .beginControlFlow("if (value == null)")
                    .addStatement("value = " + " new " + implClassName + "()")
                    .addStatement("map.put(\"" + anno.uniqueCode() + "\", value);")
                    .endControlFlow()
                    .addStatement("return value")
                    .addModifiers(Modifier.PUBLIC);

            methodSpecBuilder.addJavadoc(
                    "api \n@see " + dataSourceApiTypeElement.getQualifiedName() + "\n" +
                    "impl\n@see " + implClassName + "\n"
            );

            MethodSpec methodSpec = methodSpecBuilder.build();

            typeSpecBuilder.addMethod(methodSpec);

        }

        // 内部的Holder 静态类
        TypeSpec.Builder typeSpecHolderBuilder = TypeSpec.classBuilder("Holder")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);

        typeSpecHolderBuilder.addField(
                FieldSpec.builder(
                        ClassName.get(classNameApiManager.substring(0, classNameApiManager.lastIndexOf(".")), classNameApiManager.substring(classNameApiManager.lastIndexOf(".") + 1)), "instance"
                )
                        .initializer("new " + classNameApiManager + "()")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                        .build()
        );

        typeSpecBuilder.addJavadoc(
                "这个管理类会对所有使用 {@link com.ehi.api.EHiDataSourceAnno#impl()} 的子 DataSource 进行管理,防止创建多次\n" +
                        "使用 {@link com.ehi.api.EHiDataSourceAnno#callPath()}的 Datasource不在管理范围 \n"
        );

        TypeSpec apiTypeSpec = typeSpecBuilder
                // 添加一个私有的构造函数
                .addMethod(MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE).build())
                .addType(typeSpecHolderBuilder.build())
                .build();

        JavaFile.builder(pkgApi, apiTypeSpec)
                .indent("    ")
                .build()
                .writeTo(mFiler);

    }

    private void createDataSourceApiImpl(List<TypeElement> apiClassList) throws IOException {

        // pkg
        String pkg = classNameApiImpl.substring(0, classNameApiImpl.lastIndexOf("."));

        // simpleName
        String cn = classNameApiImpl.substring(classNameApiImpl.lastIndexOf(".") + 1);

        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(cn)
                .addModifiers(Modifier.PUBLIC);

        typeSpecBuilder.addJavadoc("这个实现类实现了全部的DataSource的接口中的方法,方法中会正确的调用对应的 DataSource\n" +
                "并且不是单例的 DataSource 的实现类内部会使用 {@link com.ehi.datasource.DataSourceManager} 去管理,避免多次初始化实现类 \n\n");

        for (TypeElement dataSourceApiTypeElement : apiClassList) {

            EHiDataSourceAnno remoteAnno = dataSourceApiTypeElement.getAnnotation(EHiDataSourceAnno.class);

            if (remoteAnno == null) {
                continue;
            }

            typeSpecBuilder.addJavadoc("@see " + dataSourceApiTypeElement.toString() + "\n");
            generateMethods(dataSourceApiTypeElement, typeSpecBuilder, remoteAnno, true);

        }

        ClassName superInterface = ClassName.get(classNameApi.substring(0, classNameApi.lastIndexOf(".")), classNameApi.substring(classNameApi.lastIndexOf(".") + 1));

        typeSpecBuilder.addSuperinterface(superInterface);

        TypeSpec typeSpec = typeSpecBuilder.build();

        JavaFile.builder(pkg, typeSpec)
                .indent("    ")
                .build()
                .writeTo(mFiler);

    }

    private void generateMethods(TypeElement dataSourceApiTypeElement, TypeSpec.Builder typeSpecBuilder, EHiDataSourceAnno anno, boolean isAddStatement) {

        // 所有可执行的方法
        List<? extends Element> enclosedElements = dataSourceApiTypeElement.getEnclosedElements();

        for (Element elementItem : enclosedElements) {

            if (!(elementItem instanceof ExecutableElement)) {
                continue;
            }

            // 可执行的方法
            ExecutableElement executableElement = (ExecutableElement) elementItem;

            // 本来写的名字
            String originalMethodName = executableElement.getSimpleName().toString();

            // 拼接出来的最终的方法名字
            String methodName = "";

            if (anno.value().isEmpty()) {
                methodName = executableElement.getSimpleName().toString();
            } else {
                methodName = anno.value() + firstCharToUp(executableElement.getSimpleName().toString());
            }

            MethodSpec.Builder methodSpecBuilder = MethodSpec.methodBuilder(methodName);

            StringBuffer sbParameterType = new StringBuffer();
            String parameterStr = generateParameters(executableElement, methodSpecBuilder, sbParameterType);

            methodSpecBuilder.returns(TypeName.get(executableElement.getReturnType()));

            if (isAddStatement) {

                methodSpecBuilder.addModifiers(Modifier.PUBLIC);

                String returnStr = null;

                if (anno.callPath().isEmpty()) {
                    returnStr = classNameApiManager + ".Holder.instance." + anno.uniqueCode() + "DataSource()." + originalMethodName + "(" + parameterStr + ")";
                } else {
                    returnStr = anno.callPath() + "." + originalMethodName + "(" + parameterStr + ")";
                }

                if ("void".equals(executableElement.getReturnType().toString())) {
                    methodSpecBuilder.addStatement(returnStr);
                } else {
                    methodSpecBuilder.addStatement("return " + returnStr);
                }

            } else {
                methodSpecBuilder
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT);
            }

            methodSpecBuilder.addJavadoc("点击相应的 Link 会帮助您跳转到对应的接口和实现类 \n");
            methodSpecBuilder.addJavadoc("api： \n@see " + dataSourceApiTypeElement.toString() + "#" + originalMethodName + "(" + sbParameterType.toString() + ") \n");
            // 如果是实现类的方式的
            if (getImplClassName(anno) != null) {
                methodSpecBuilder.addJavadoc("impl：\n@see " + getImplClassName(anno) + "#" + originalMethodName + "(" + sbParameterType.toString() + ") \n");
            } else {
            }

            // {@link MockLocationDataSourceImpl#info()}

            MethodSpec methodSpec = methodSpecBuilder.build();

            typeSpecBuilder.addMethod(methodSpec);

        }

    }

    /**
     * 生成方法的参数列表：pageSize,pageIndex
     *
     * @param executableElement
     * @param methodSpecBuilder
     * @param sbParameterType   用来记录参数类型 (Integer,String)
     * @return
     */
    private String generateParameters(ExecutableElement executableElement, MethodSpec.Builder methodSpecBuilder, StringBuffer sbParameterType) {

        List<? extends VariableElement> typeParameters = executableElement.getParameters();

        StringBuffer sb = new StringBuffer();

        for (VariableElement typeParameter : typeParameters) {

            // 拿到参数的类型
            TypeName typeName = TypeName.get(typeParameter.asType());

            // 得到声明的参数的名字
            String parameterName = typeParameter.getSimpleName().toString();

            // 构建一个参数对象
            ParameterSpec parameterSpec = ParameterSpec.builder(typeName, parameterName)
                    .build();

            if (sbParameterType != null) {
                if (sbParameterType.length() > 0) {
                    sbParameterType.append(",");
                }
                sbParameterType.append((typeName.toString()));
            }

            // 拼接出调用的方法的参数比如：a.b(a,b)
            if (sb.length() == 0) {
                sb.append(parameterName);
            } else {
                sb.append(",").append(parameterName);
            }

            methodSpecBuilder.addParameter(parameterSpec);

        }

        return sb.toString();

    }

    private String firstCharToUp(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }

        String str1 = str.substring(0, 1).toUpperCase();
        String str2 = str.substring(1);

        return str1 + str2;

    }

    private String getLastSubString(String str) {
        int index = str.lastIndexOf('.');
        if (index > -1 && index < str.length() - 1) {
            return str.substring(index + 1);
        } else {
            return str;
        }
    }

    private String getImplClassName(EHiDataSourceAnno anno) {
        String implClassName = null;
        try {
            implClassName = anno.impl().getName();
        } catch (MirroredTypeException e) {
            implClassName = e.getTypeMirror().toString();
        }
        if ("java.lang.Void".equals(implClassName)) {
            return null;
        }
        return implClassName;
    }

}
