package services

import play.api.Configuration
import play.api.libs.json.{Json, Writes}
import play.api.libs.ws.{WSClient, WSResponse}

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ChatGPTService @Inject()(config: Configuration, ws: WSClient)(implicit ec: ExecutionContext) {

  private val apiKey: String = config.get[String]("openai.apiKey")
  private val apiUrl: String = "https://api.openai.com/v1/engines/gpt-3.5-turbo-instruct/completions"
  println("key.."+apiKey)
  def chat(prompt: String): Future[String] = {
    val requestData = Json.obj(
      "prompt" -> prompt,
      "max_tokens" -> 150
    )

    ws.url(apiUrl)
      .addHttpHeaders("Authorization" -> s"Bearer $apiKey", "Content-Type" -> "application/json")
      .post(requestData)
      .map(handleResponse)
  }

  private def handleResponse(response: WSResponse): String = {
    if (response.status == 200) {
      //(response.json \ "choices")(0) \ "text"
      Json.stringify(response.json)
    } else {
      s"Error ${response.status}: ${response.body}"
    }
  }
}