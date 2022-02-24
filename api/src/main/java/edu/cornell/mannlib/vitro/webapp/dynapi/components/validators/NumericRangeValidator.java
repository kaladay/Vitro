package edu.cornell.mannlib.vitro.webapp.dynapi.components.validators;

import edu.cornell.mannlib.vitro.webapp.utils.configuration.Property;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NumericRangeValidator extends IsNotBlank {
	
	private static final Log log = LogFactory.getLog(NumericRangeValidator.class);

	private Float minValue;

	private Float maxValue;

	public Float getMinValue() {
		return minValue;
	}

	@Property(uri = "https://vivoweb.org/ontology/vitro-dynamic-api#validatorMinNumericValue", minOccurs = 1, maxOccurs = 1)
	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public Float getMaxValue() {
		return maxValue;
	}

	@Property(uri = "https://vivoweb.org/ontology/vitro-dynamic-api#validatorMaxNumericValue", minOccurs = 1, maxOccurs = 1)
	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}

	@Override
	public boolean isValid(String name, String[] values) {
		if (!super.isValid(name, values)) {
			return false;
		}
		for (String value : values) {
			if (!isInRange(value)) {
				return false;
			}
		}
		return true;
	}

	private boolean isInRange(String string) {
		if (NumberUtils.isParsable(string)) {
			double value = NumberUtils.toDouble(string);

			if ((minValue != null) && (value < minValue))
				return false;

			if ((maxValue != null) && (value > maxValue))
				return false;

			return true;
		}
		return false;
	}
}
