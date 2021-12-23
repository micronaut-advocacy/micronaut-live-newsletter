package io.micronaut.live.views

import io.micronaut.core.beans.BeanIntrospection
import spock.lang.Specification

class HtmlPageSpec extends Specification {
    void "io.micronaut.live.views.HtmlPage is annotated with @Introspected"() {
        when:
        BeanIntrospection.getIntrospection(HtmlPage)

        then:
        noExceptionThrown()
    }
}
