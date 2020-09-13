package first_B.com.firstb.proj;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

/**
 * Hello world!
 *
 */
public class App {
	final String runMessage = "Something went wrong! Bye";

	private void deployForEeventLoop(int instances) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(SimpleREST.class.getName(), new DeploymentOptions().setInstances(instances));

	}

	public static void main(String[] args) {
		new App().start();
	}

	public void start() {
		deployForEeventLoop(2);
		System.out.println("Vert.x deployed on port 8443");
	}
}
