package alura.com.br.screensound.screensound.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterInformacao(String texto) {
        OpenAiService service = new OpenAiService(System.getenv("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"));


        CompletionRequest requisicao = CompletionRequest.builder()
                .model("text-davinci-003")
                .prompt("me fale sobre o artista: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();


        var resposta = service.createCompletion(requisicao);
        return resposta.getChoices().get(0).getText();
    }
}
