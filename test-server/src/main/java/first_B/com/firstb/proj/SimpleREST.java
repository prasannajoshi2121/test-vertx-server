package first_B.com.firstb.proj;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class SimpleREST extends AbstractVerticle {

	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		router.route().handler(BodyHandler.create());
		router.post("/testIOExecBlocking").handler(this::testIOExecBlocking);
		router.post("/eventloopAsynch").handler(this::eventloopAsynch);
		vertx.createHttpServer(new HttpServerOptions()).requestHandler(router).listen(8443);

	}

	private void eventloopAsynch(RoutingContext routingContext) {
		try {
			System.out.println("eventloopAsynch thread info" + Thread.currentThread().getName());
			routingContext.response().end("Fast response");
		} catch (Exception e) {
			e.printStackTrace();
			sendError(500, routingContext.response());
			// TODO: handle exception
		}
	}

	private void testIOExecBlocking(RoutingContext routingContext) {
		vertx.executeBlocking(v -> {
			try {
				System.out.println("testIOExecBlocking thread info " + Thread.currentThread().getName());
				Thread.sleep(300);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				sendError(500, routingContext.response());
			}
			v.complete("Slow response");
		}, resultHandler -> {
			routingContext.response().setStatusCode(200).end("slow respoonse");
		});

	}

	private void sendError(int code, HttpServerResponse re) {
		re.setStatusCode(code).end();
	}

}
