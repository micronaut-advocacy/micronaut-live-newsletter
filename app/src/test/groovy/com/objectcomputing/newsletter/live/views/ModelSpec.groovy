package com.objectcomputing.newsletter.live.views

import io.micronaut.core.beans.BeanIntrospection
import spock.lang.Specification

class ModelSpec extends Specification {
    void "com.objectcomputing.newsletter.live.views.HtmlPage is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(Model)

        then:
        noExceptionThrown()
    }
}
