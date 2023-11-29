package controllers

import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import services.ChatGPTService

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ChatController @Inject()(cc: ControllerComponents, chatGPTService: ChatGPTService)
                              (implicit ec: ExecutionContext) extends AbstractController(cc) {

  def chat(text: String): Action[AnyContent] = Action.async { implicit request =>
    val prompt = text//request.body.asText.getOrElse("")

    chatGPTService.chat(prompt).map { response =>
      println(response)
      Ok(response)
    }
  }
}
