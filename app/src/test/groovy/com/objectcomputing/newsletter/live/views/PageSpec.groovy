package com.objectcomputing.newsletter.live.views

import spock.lang.Specification

import java.util.function.Function

class PageSpec extends Specification {

    void "Page implements Comparable"() {
        given:
        Function<Integer, String> f = new Function<Integer, String>() {
            @Override
            String apply(Integer integer) {
                return '/subscriber/list'
            }
        }
        List<Page> l = [new Page(2, f),
                        new Page(1, f),
                        new Page(3, f)]

        when:
        Collections.sort(l)

        then:
        l.first().number == 1
        l.last().number == 3
    }
}
