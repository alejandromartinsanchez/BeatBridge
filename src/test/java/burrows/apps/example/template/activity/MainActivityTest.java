package burrows.apps.example.template.activity;

import static org.assertj.core.api.Assertions.assertThat;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public final class MainActivityTest {
  @Rule
  public final ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

  @Test
  public void testOnCreateNotNull() {
    activityScenarioRule.getScenario().onActivity(activity -> assertThat(activity).isNotNull());
  }
}
