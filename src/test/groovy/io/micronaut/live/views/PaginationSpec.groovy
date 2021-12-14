package io.micronaut.live.views

import spock.lang.Specification

class PaginationSpec extends Specification {

    void "Pagination encapsulates a pagination component"() {
        when:
        List<Page> pages = [new Page(2, '/subscriber/list', true),
                            new Page(1, '/subscriber/list'), new Page(3, '/subscriber/list')]
        Pagination pagination = new Pagination(pages)

        then:
        pagination.hasNext()
        pagination.hasPrevious()

        when:
        pages = [new Page(2, '/subscriber/list'), new Page(1, '/subscriber/list', true), new Page(3, '/subscriber/list')]
        pagination = new Pagination(pages)

        then:
        pagination.hasNext()
        !pagination.hasPrevious()

        when:
        pages = [new Page(2, '/subscriber/list'), new Page(1, '/subscriber/list'), new Page(3, '/subscriber/list', true)]
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
        new Pagination([new Page(2, '/subscriber/list'), new Page(1, '/subscriber/list'), new Page(3, '/subscriber/list')])

        then:
        thrown(IllegalArgumentException)
    }

    void "Pagination.of to build Pagination object"() {
        when:
        Pagination pagination = Pagination.of(50, 12, '/subscriber/list', 1)

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
        pagination = Pagination.of(50, 5, '/subscriber/list', 3)

        then:
        pagination.getPages().size() == 10

        pagination.getPages().get(2).number == 3
        pagination.getPages().get(2).active
    }
}
