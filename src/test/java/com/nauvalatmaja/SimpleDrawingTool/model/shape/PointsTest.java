package com.nauvalatmaja.SimpleDrawingTool.model.shape;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.Test;

public class PointsTest {
	
	private Points classUnderTest;

	@Before
	public void setUp() throws Exception {
		classUnderTest = new Points(0, 0, 10, 10);
	}

	@Test
	public void testGetXStart() {
		assertThat(classUnderTest.getXStart(), is(0.));
	}

	@Test
	public void testSetXStart() {
		classUnderTest.setXStart(10);
		assertThat(classUnderTest.getXStart(), is(10.));
	}

	@Test
	public void testGetYStart() {
		assertThat(classUnderTest.getYStart(), is(0.));
	}

	@Test
	public void testSetYStart() {
		classUnderTest.setYStart(10);
		assertThat(classUnderTest.getYStart(), is(10.));
	}

	@Test
	public void testGetXEnd() {
		assertThat(classUnderTest.getXEnd(), is(10.));
	}

	@Test
	public void testSetXEnd() {
		classUnderTest.setXEnd(5);
		assertThat(classUnderTest.getXEnd(), is(5.));
	}

	@Test
	public void testGetYEnd() {
		assertThat(classUnderTest.getYEnd(), is(10.));
	}

	@Test
	public void testSetYEnd() {
		classUnderTest.setYEnd(5);
		assertThat(classUnderTest.getYEnd(), is(5.));
	}

}
