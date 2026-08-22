package com.signwell.sdk.internal;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.signwell.sdk.embedded.Embedded;
import com.signwell.sdk.models.AdditionalFieldsInnerInner;
import com.signwell.sdk.models.AdditionalFieldsInnerInnerValue;
import com.signwell.sdk.models.FieldType;
import com.signwell.sdk.models.FieldsInnerInner;
import com.signwell.sdk.models.TemplateFieldsInnerInner;
import java.util.ArrayList;
import java.util.List;

/** Normalizes checkbox request values while preserving non-checkbox and response values. */
public final class CheckboxValueModule extends SimpleModule {
    public CheckboxValueModule() {
        setSerializerModifier(new Modifier());
    }

    private static final class Modifier extends BeanSerializerModifier {
        @Override
        public List<BeanPropertyWriter> changeProperties(
                SerializationConfig config,
                BeanDescription description,
                List<BeanPropertyWriter> properties) {
            Class<?> type = description.getBeanClass();
            if (type != FieldsInnerInner.class
                    && type != TemplateFieldsInnerInner.class
                    && type != AdditionalFieldsInnerInner.class) return properties;
            List<BeanPropertyWriter> result = new ArrayList<>(properties.size());
            for (BeanPropertyWriter property : properties) {
                result.add(
                        "value".equals(property.getName())
                                ? new CheckboxWriter(property)
                                : property);
            }
            return result;
        }
    }

    private static final class CheckboxWriter extends BeanPropertyWriter {
        CheckboxWriter(BeanPropertyWriter source) {
            super(source);
        }

        @Override
        public void serializeAsField(
                Object bean, JsonGenerator generator, SerializerProvider provider)
                throws Exception {
            AdditionalFieldsInnerInnerValue value = value(bean);
            if (type(bean) != FieldType.CHECKBOX || value == null) {
                super.serializeAsField(bean, generator, provider);
                return;
            }
            generator.writeFieldName(getName());
            generator.writeString(Embedded.normalizeCheckbox(value.getActualInstance()));
        }

        private static FieldType type(Object bean) {
            if (bean instanceof FieldsInnerInner) return ((FieldsInnerInner) bean).getType();
            if (bean instanceof TemplateFieldsInnerInner)
                return ((TemplateFieldsInnerInner) bean).getType();
            return ((AdditionalFieldsInnerInner) bean).getType();
        }

        private static AdditionalFieldsInnerInnerValue value(Object bean) {
            if (bean instanceof FieldsInnerInner) return ((FieldsInnerInner) bean).getValue();
            if (bean instanceof TemplateFieldsInnerInner)
                return ((TemplateFieldsInnerInner) bean).getValue();
            return ((AdditionalFieldsInnerInner) bean).getValue();
        }
    }
}
