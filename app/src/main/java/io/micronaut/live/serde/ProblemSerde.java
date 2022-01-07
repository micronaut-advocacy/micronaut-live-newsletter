package io.micronaut.live.serde;

import io.micronaut.problem.HttpStatusType;
import io.micronaut.serde.annotation.SerdeImport;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Exceptional;

//TODO delete this file when this PR is merged and new version of problem is released
// https://github.com/micronaut-projects/micronaut-problem-json/pull/95
@SerdeImport(Exceptional.class)
@SerdeImport(HttpStatusType.class)
@SerdeImport(DefaultProblem.class)
public class ProblemSerde {
}
