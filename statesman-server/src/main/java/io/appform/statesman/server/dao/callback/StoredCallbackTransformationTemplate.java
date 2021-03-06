package io.appform.statesman.server.dao.callback;

import io.appform.statesman.server.callbacktransformation.TransformationTemplateType;
import io.appform.statesman.server.callbacktransformation.TranslationTemplateType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "callback_templates", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"provider", "translation_template_type"})
})
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", length = 45)
@NoArgsConstructor
public abstract class StoredCallbackTransformationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "type", insertable = false, updatable = false)
    private TransformationTemplateType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "translation_template_type")
    private TranslationTemplateType translationTemplateType;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "id_path")
    private String idPath;

    @Column(name = "fql_path")
    private String fqlPath;

    @Column(name = "template", columnDefinition = "blob")
    private byte[] template;

    @Column(name = "drop_detection_rule")
    String dropDetectionRule;

    @Column(name = "created", columnDefinition = "datetime default current_timestamp", updatable = false, insertable = false)
    @Generated(value = GenerationTime.INSERT)
    private Date created;

    @Column(name = "updated", columnDefinition = "datetime", updatable = false, insertable = false)
    @Generated(value = GenerationTime.ALWAYS)
    private Date updated;

    protected StoredCallbackTransformationTemplate(
            TransformationTemplateType type,
            String provider,
            String idPath,
            String fqlPath,
            TranslationTemplateType translationTemplateType,
            byte[] template,
            String dropDetectionRule) {
        this.type = type;
        this.provider = provider;
        this.idPath = idPath;
        this.fqlPath = fqlPath;
        this.translationTemplateType = translationTemplateType;
        this.template = template;
        this.dropDetectionRule = dropDetectionRule;
    }

    public abstract <T> T visit(StoredCallbackTransformationTemplateVisitor<T> visitor);
}


