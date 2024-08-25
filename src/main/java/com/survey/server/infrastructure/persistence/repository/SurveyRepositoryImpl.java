package com.survey.server.infrastructure.persistence.repository;

import com.survey.server.domain.exception.SurveyNotFoundException;
import com.survey.server.domain.model.Survey;
import com.survey.server.domain.repository.SurveyRepository;
import com.survey.server.infrastructure.persistence.model.SurveyModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class SurveyRepositoryImpl implements SurveyRepository {

    private final MongoTemplate mongo;

    public SurveyRepositoryImpl(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public Survey save(Survey survey) {
        SurveyModel surveyModel = new SurveyModel(survey);
        return mongo.save(surveyModel).toDomain();
    }

    @Override
    public Optional<Survey> findById(String id) {
        Query query = new Query(Criteria.where("_id").is(id).and("is_deleted").is(false));
        SurveyModel surveyById = mongo.find(query, SurveyModel.class)
                .stream()
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(surveyById).map(SurveyModel::toDomain);

    }

    @Override
    public List<Survey> findAll() {
        Query query = new Query(Criteria.where("is_deleted").is(false));
        return mongo.find(query, SurveyModel.class)
                .stream()
                .map(SurveyModel::toDomain)
                .toList();
    }

    @Override
    public void deleteById(String id) {
        SurveyModel surveyModel =
                Optional.ofNullable(mongo.findById(id, SurveyModel.class))
                        .orElseThrow(SurveyNotFoundException::new);

        surveyModel.setIs_deleted(true);
        surveyModel.setDeleted_at(LocalDateTime.now());

        mongo.save(surveyModel);
    }
}