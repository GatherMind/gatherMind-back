package woongjin.gatherMind.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import woongjin.gatherMind.enums.StudyRole;

//@Converter(autoApply = true)가 설정되어 있다면, 해당 Role 타입 필드에 자동으로 적용됩니다.
@Converter(autoApply = true)
public class StudyRoleConverter implements AttributeConverter<StudyRole, Integer> {
    @Override
    public Integer convertToDatabaseColumn(StudyRole role) {
        return (role == null) ? null : role.getCode();
    }

    @Override
    public StudyRole convertToEntityAttribute(Integer integer) {
        if (integer == null) {
            return null;
        }
        return StudyRole.fromCode(integer);
    }

}
