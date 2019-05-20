package com.ypb.jmh;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.CompilerControl;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class JMHSampleCompilerControl {

	public void targetBlank() {

	}

	@CompilerControl(CompilerControl.Mode.DONT_INLINE)
	public void targetDontInline() {

	}

	@CompilerControl(CompilerControl.Mode.INLINE)
	public void targetInline() {

	}

	@Benchmark
	public void baseline() {

	}

	@Benchmark
	public void dontInline() {
		targetDontInline();
	}

	@Benchmark
	public void inLine() {
		targetInline();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(JMHSampleCompilerControl.class.getSimpleName()).warmupIterations(0)
				.measurementIterations(3).forks(1).build();

		new Runner(opt).run();
	}
}
