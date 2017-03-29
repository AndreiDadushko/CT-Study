package com.andreidadushko.tomography2017.services;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;

import com.andreidadushko.tomography2017.datamodel.StudyProtocol;

public class StudyProtocolServiceTest extends AbstractTest{
	
	@Inject
	private IStudyProtocolService studyProtocolService;
	
	private static List<StudyProtocol> testData;
	
	@Before
	public void initializeTestData() {
		
	}
}
