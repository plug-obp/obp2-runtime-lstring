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

package obp2.language.lstring.runtime;

import obp2.core.IFiredTransition;
import obp2.core.defaults.FiredTransition;
import obp2.language.lstring.model.LStringProgram;
import obp2.runtime.core.IConcurrentTransitionRelation;
import obp2.runtime.core.ITransitionRelation;
import obp2.runtime.core.defaults.DefaultLanguageService;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class LStringTransitionRelation
		extends DefaultLanguageService<LStringConfiguration, LStringAction, Void>
		implements ITransitionRelation<LStringConfiguration, LStringAction, Void>,
		IConcurrentTransitionRelation<LStringTransitionRelation, LStringConfiguration, LStringAction, Void> {

	public String name;

	public LStringProgram program;

	public LStringTransitionRelation(String name, LStringProgram program) {
		this.name = name;
		this.program = program;
	}

	@Override
	public LStringTransitionRelation createCopy() {
		return new LStringTransitionRelation(name, program);
	}
	
	@Override
	public Set<LStringConfiguration> initialConfigurations() {
		Set<LStringConfiguration> initialSet = new HashSet<>();
		LStringConfiguration config = new LStringConfiguration();
		config.index = 0;
		return Collections.singleton(config);
	}

	@Override
	public Collection<LStringAction> fireableTransitionsFrom(LStringConfiguration source) {
		if (source.index + 1 >= program.string.length()) return Collections.emptyList();

		return Collections.singleton(LStringAction.getInstance());
	}

	@Override
	public IFiredTransition<LStringConfiguration, LStringAction, Void> fireOneTransition(LStringConfiguration source, LStringAction transition) {
		//create the new configuration
		LStringConfiguration target = new LStringConfiguration();
		target.index 	  = source.index + 1;

		return new FiredTransition<>(source, target, transition);
	}
}
