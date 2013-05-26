/**************************************************************************************************
 * This file is part of [SpringAtom] Copyright [kornicameister@gmail.com][2013]                   *
 *                                                                                                *
 * [SpringAtom] is free software: you can redistribute it and/or modify                           *
 * it under the terms of the GNU General Public License as published by                           *
 * the Free Software Foundation, either version 3 of the License, or                              *
 * (at your option) any later version.                                                            *
 *                                                                                                *
 * [SpringAtom] is distributed in the hope that it will be useful,                                *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                                 *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                                  *
 * GNU General Public License for more details.                                                   *
 *                                                                                                *
 * You should have received a copy of the GNU General Public License                              *
 * along with [SpringAtom].  If not, see <http://www.gnu.org/licenses/gpl.html>.                  *
 **************************************************************************************************/

package org.agatom.springatom.annotations.processors;

import org.agatom.springatom.annotations.GenCache;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */

@SupportedSourceVersion(value = SourceVersion.RELEASE_7)
@SupportedAnnotationTypes(value = {
        "org.agatom.springatom.annotations.GenCache"
})
public class CacheGeneratorProcessor extends AbstractProcessor implements Filer {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (Element elem : roundEnv.getElementsAnnotatedWith(GenCache.class)) {
            GenCache cacheEntry = elem.getAnnotation(GenCache.class);
            System.out.println(cacheEntry.daoName());
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    String.format("Class %s annotated to generate cache entry %s",
                            elem.getSimpleName(), cacheEntry.daoName())
            );
        }
        return true;
    }

    @Override
    public JavaFileObject createSourceFile(final CharSequence name,
                                           final Element... originatingElements) throws IOException {
        return null;
    }

    @Override
    public JavaFileObject createClassFile(final CharSequence name,
                                          final Element... originatingElements) throws IOException {
        return null;
    }

    @Override
    public FileObject createResource(final JavaFileManager.Location location,
                                     final CharSequence pkg,
                                     final CharSequence relativeName,
                                     final Element... originatingElements) throws IOException {
        return null;
    }

    @Override
    public FileObject getResource(final JavaFileManager.Location location,
                                  final CharSequence pkg,
                                  final CharSequence relativeName) throws IOException {
        return null;
    }
}
