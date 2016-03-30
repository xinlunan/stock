package com.winit.junit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.winit.wunit.runners.ParameterizedExt;
import com.winit.wunit.runners.ParameterizedExt.Parameters;

@RunWith(ParameterizedExt.class)
@ContextConfiguration("classpath*:config/applicationContext.xml")
public class TestAnnatation {

	@Parameters
	public static List<Object[]> data() {

		return Arrays.asList(new Object[][] { { -1, 1, 0 }, { 20, 20, 40 }, { 30, 30, 60 }, { -5, -5, -10 } });
	}

	public TestAnnatation(int a, int b, int c) {
		o1 = a;
		o2 = b;
		expector = c;
	}

	public int o1;
	public int o2;
	public int expector;

	@Test
	public void test() throws IOException, RuntimeException {
		assertEquals(expector, o1+o2);
	}

}
