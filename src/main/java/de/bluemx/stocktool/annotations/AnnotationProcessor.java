package de.bluemx.stocktool.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.util.Set;

/**
 * Created by Patrick Labonte on 13.12.2016.
 */
@SupportedAnnotationTypes("de.bluemx.stocktool.annotations.Config")
public class AnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("TEST");
        return false;
    }


}
