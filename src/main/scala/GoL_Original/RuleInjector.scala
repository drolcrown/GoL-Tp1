package GoL_Original
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

import Rules.{HighLife, CustomStrategy}

/**
  * Created by RafaelSouza on 04/10/2017.
  */

class RuleInjector extends AbstractModule with ScalaModule {
  def configure(): Unit = {
//    bind[GameEngine.type].to[Rules.CustomStrategy.type]
//    bind[Rules.HighLife.type].to[Rules.HighLife.type].in[GameEngine.type]
  }

}
