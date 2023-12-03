/*
 * MIT License
 *
 * Copyright (c) 2023 Ciprian Teodorov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package obp2.language.lstring;

import obp2.language.lstring.diagnosis.LStringAtomicPropositionEvaluator;
import obp2.language.lstring.model.LStringProgram;
import obp2.language.lstring.runtime.LStringAction;
import obp2.language.lstring.runtime.LStringConfiguration;
import obp2.language.lstring.runtime.LStringTransitionRelation;
import obp2.runtime.core.ILanguageModule;
import obp2.runtime.core.ILanguagePlugin;
import obp2.runtime.core.LanguageModule;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.function.Function;

/**
 * Created by Ciprian TEODOROV on 03/03/17.
 */
public class LStringPlugin implements ILanguagePlugin<URI, LStringConfiguration, LStringAction, Void> {

    @Override
    public String[] getExtensions() {
        return new String[]{".lstring"};
    }

    @Override
    public Function<URI, ILanguageModule<LStringConfiguration, LStringAction, Void>> languageModuleFunction() {
        return this::getRuntime;
    }

    public ILanguageModule<LStringConfiguration, LStringAction, Void> getRuntime(URI explicitProgramURI) {
        return getRuntime(new File(explicitProgramURI));
    }

    public ILanguageModule<LStringConfiguration, LStringAction, Void> getRuntime(String explicitProgramFileName) {
        return getRuntime(new File(explicitProgramFileName));
    }

    public ILanguageModule<LStringConfiguration, LStringAction, Void> getRuntime(File programFile) {
        try {
            String string = new String(Files.readAllBytes(programFile.toPath()));
            LStringProgram program = new LStringProgram(string);
            LStringTransitionRelation runtime = new LStringTransitionRelation(programFile.getName(), program);

            return new LanguageModule<>(runtime, new LStringAtomicPropositionEvaluator(program), new LStringRuntimeView(runtime));
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getName() {
        return "LString";
    }


}
