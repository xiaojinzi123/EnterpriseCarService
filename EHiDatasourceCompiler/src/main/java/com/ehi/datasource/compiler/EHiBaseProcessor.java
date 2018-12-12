package com.ehi.datasource.compiler;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * time   : 2018/09/11
 *
 * @author : xiaojinzi 30212
 */
public abstract class EHiBaseProcessor extends AbstractProcessor {

    protected Filer mFiler;
    protected Messager mMessager;
    protected Types mTypes;
    protected Elements mElements;

    protected TypeMirror typeString;
    protected TypeMirror typeVoid;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mFiler = processingEnv.getFiler();
        mMessager = processingEnvironment.getMessager();
        mTypes = processingEnv.getTypeUtils();
        mElements = processingEnv.getElementUtils();

        typeString = mElements.getTypeElement("java.lang.String").asType();
        typeVoid = mElements.getTypeElement("java.lang.Void").asType();

    }

}
