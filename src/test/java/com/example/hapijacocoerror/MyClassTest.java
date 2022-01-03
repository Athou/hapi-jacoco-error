package com.example.hapijacocoerror;

import org.hl7.fhir.common.hapi.validation.support.CachingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.CommonCodeSystemsTerminologyService;
import org.hl7.fhir.common.hapi.validation.support.InMemoryTerminologyServerValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.SnapshotGeneratingValidationSupport;
import org.hl7.fhir.common.hapi.validation.support.ValidationSupportChain;
import org.hl7.fhir.common.hapi.validation.validator.FhirInstanceValidator;
import org.hl7.fhir.r4.model.AllergyIntolerance;
import org.hl7.fhir.r4.model.Practitioner;
import org.hl7.fhir.r4.model.Reference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.context.support.DefaultProfileValidationSupport;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.ValidationResult;

class MyClassTest {

	@Test
	void test() {
		FhirContext context = FhirContext.forR4();
		FhirValidator validator = context.newValidator();

		ValidationSupportChain validationSupportChain = new ValidationSupportChain(new DefaultProfileValidationSupport(context),
				new CommonCodeSystemsTerminologyService(context), new InMemoryTerminologyServerValidationSupport(context),
				new SnapshotGeneratingValidationSupport(context));
		CachingValidationSupport validationSupport = new CachingValidationSupport(validationSupportChain);
		validator.registerValidatorModule(new FhirInstanceValidator(validationSupport));

		AllergyIntolerance allergyIntolerance = new AllergyIntolerance();
		allergyIntolerance.setId("1");
		allergyIntolerance.setRecorder(new Reference(new Practitioner().setId("1")));

		ValidationResult validationResult = validator.validateWithResult(allergyIntolerance);
		Assertions.assertFalse(validationResult.isSuccessful(), validationResult.toString());

	}

}
