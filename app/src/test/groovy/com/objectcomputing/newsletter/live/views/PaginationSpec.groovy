package com.objectcomputing.newsletter.live.views

import spock.lang.Specification

import java.util.function.Function

class PaginationSpec extends Specification {

    void "Pagination encapsulates a pagination component"() {
        given:
        Function<Integer, String> f = new Function<Integer, String>() {
            @Override
            String apply(Integer integer) {
                return '/subscriber/list'
            }
        }

        when:
        List<Page> pages = [
                new Page(2, f, true),
                new Page(1, f),
                new Page(3, f)
        ]
        Pagination pagination = new Pagination(pages)

        then:
        pagination.hasNext()
        pagination.hasPrevious()

        when:
        pages = [new Page(2, f), new Page(1, f, true), new Page(3, f)]
        pagination = new Pagination(pages)

        then:
        pagination.hasNext()
        !pagination.hasPrevious()

        when:
        pages = [new Page(2, f), new Page(1, f), new Page(3, f, true)]
        pagination = new Pagination(pages)

        then:
        !pagination.hasNext()
        pagination.hasPrevious()

        when:
        pages = []
        pagination = new Pagination(pages)

        then:
        !pagination.hasNext()
        !pagination.hasPrevious()

        when:
        new Pagination([new Page(2, f), new Page(1, f), new Page(3, f)])

        then:
        thrown(IllegalArgumentException)
    }

    void "Pagination.of to build Pagination object"() {
        given:
        Function<Integer, String> f = new Function<Integer, String>() {
            @Override
            String apply(Integer integer) {
                return '/subscriber/list'
            }
        }

        when:
        Pagination pagination = Pagination.of(50, 12, f, 1)

        then:
        pagination.getPages().size() == 5
        pagination.getPages().get(0).number == 1
        pagination.getPages().get(0).active

        pagination.getPages().get(1).number == 2
        !pagination.getPages().get(1).active

        pagination.getPages().get(2).number == 3
        !pagination.getPages().get(2).active

        pagination.getPages().get(3).number == 4
        !pagination.getPages().get(3).active

        pagination.getPages().get(4).number == 5
        !pagination.getPages().get(4).active

        when:
        pagination = Pagination.of(50, 5, f, 3)

        then:
        pagination.getPages().size() == 10

        pagination.getPages().get(2).number == 3
        pagination.getPages().get(2).active
    }
}
