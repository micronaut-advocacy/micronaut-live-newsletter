package com.objectcomputing.newsletter.live.views

import spock.lang.Specification

class PageSpec extends Specification {

    void "Page implements Comparable"() {
        given:
        List<Page> l = [new Page(2, '/subscriber/list'),
                        new Page(1, '/subscriber/list'),
                        new Page(3, '/subscriber/list')]

        when:
        Collections.sort(l)

        then:
        l.first().number == 1
        l.last().number == 3
    }
}
