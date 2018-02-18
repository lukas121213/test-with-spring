package com.testwithspring.master.task;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;
import com.testwithspring.master.Tasks;
import com.testwithspring.master.TestTags;
import com.testwithspring.master.config.Profiles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@JooqTest
@Import(TaskjOOQRepository.class)
@TestExecutionListeners(value =  DbUnitTestExecutionListener.class,
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@DbUnitConfiguration(dataSetLoader = ReplacementDataSetLoader.class)
@DatabaseSetup({
        "/com/testwithspring/master/users.xml",
        "/com/testwithspring/master/tasks.xml"
})
@ActiveProfiles(Profiles.INTEGRATION_TEST)
@Tag(TestTags.INTEGRATION_TEST)
@DisplayName("Find all tasks from the database when two tasks are found")
class FindAllTasksWhenTwoTasksAreFoundjOOQTest {

    @Autowired
    private TaskjOOQRepository repository;

    @Test
    @DisplayName("Should return two tasks")
    void shouldReturnTwoTasks() {
        List<TaskListDTO> tasks = repository.findAll();
        assertThat(tasks).hasSize(2);
    }

    @Test
    @DisplayName("Should return the correct information of the first task")
    void shouldReturnCorrectInformationOfFirstTask() {
        TaskListDTO first = repository.findAll().get(0);

        assertThat(first.getId()).isEqualByComparingTo(Tasks.WriteExampleApp.ID);
        assertThat(first.getStatus()).isEqualTo(Tasks.WriteExampleApp.STATUS);
        assertThat(first.getTitle()).isEqualTo(Tasks.WriteExampleApp.TITLE);
    }

    @Test
    @DisplayName("Should return the correct information of the second task")
    void shouldReturnCorrectInformationOfSecondTask() {
        TaskListDTO second = repository.findAll().get(1);

        assertThat(second.getId()).isEqualByComparingTo(Tasks.WriteLesson.ID);
        assertThat(second.getStatus()).isEqualTo(Tasks.WriteLesson.STATUS);
        assertThat(second.getTitle()).isEqualTo(Tasks.WriteLesson.TITLE);
    }
}
