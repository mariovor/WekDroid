package eu.hquer.wekdroid;

import java.util.List;

import eu.hquer.wekdroid.model.Board;
import eu.hquer.wekdroid.model.Card;
import eu.hquer.wekdroid.model.Token;
import eu.hquer.wekdroid.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mariovor on 22.02.18.
 */

public interface WekanService {
    @POST("users/login")
    Call<Token> authenticate(@Body User user);

    @GET("api/users/{id}/boards")
    Call<List<Board>> getBoards(@Path("id") String id, @Header("Authorization") String authHeader);

    @GET("api/boards/{board_id}/lists")
    Call<List<Board>> getListOfBoards(@Path("board_id") String board_id, @Header("Authorization") String authHeader);

    @GET("api/boards/{board_id}/lists/{list_id}")
    Call<List<Card>> getCardsOfList(@Path("board_id") String board_id, @Path("list_id") String list_id, @Header("Authorization") String authHeader);
}
