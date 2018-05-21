package eu.hquer.wekdroid;

import java.util.List;

import eu.hquer.wekdroid.model.Board;
import eu.hquer.wekdroid.model.Card;
import eu.hquer.wekdroid.model.Swimlane;
import eu.hquer.wekdroid.model.User;
import eu.hquer.wekdroid.model.WekanList;
import eu.hquer.wekdroid.model.WekanToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by mariovor on 22.02.18.
 */

public interface WekanService {
    @POST("users/login")
    Call<WekanToken> authenticate(@Body User user);

    @GET("api/users/{id}/boards")
    Call<List<Board>> getBoards(@Path("id") String id, @Header("Authorization") String authHeader);

    @GET("api/boards/{board_id}/lists")
    Call<List<WekanList>> getListOfBoards(@Path("board_id") String board_id, @Header("Authorization") String authHeader);

    @GET("api/boards/{board_id}/swimlanes")
    Call<List<Swimlane>> getSwimlanes(@Path("board_id") String board_id, @Header("Authorization") String authHeader);

    @GET("api/boards/{board_id}/lists/{list_id}/cards")
    Call<List<Card>> getListOfCards(@Path("board_id") String board_id, @Path("list_id") String list_id, @Header("Authorization") String authHeader);

    @POST("api/boards/{board_id}/lists/{list_id}/cards")
    Call<List<Card>> AddCard(@Path("board_id") String board_id, @Path("list_id") String list_id, @Body Card card, @Header("Authorization") String authHeader);

    @GET("api/boards/{board_id}/lists/{list_id}/cards/{card_id}")
    Call<Card> getCard(@Path("board_id") String board_id, @Path("list_id") String list_id, @Path("card_id") String card_id,  @Header("Authorization") String authHeader);

    @PUT("api/boards/{board_id}/lists/{list_id}/cards/{card_id}")
    Call<Card> updateCard(@Path("board_id") String board_id, @Path("list_id") String list_id, @Path("card_id") String card_id, @Body Card card,  @Header("Authorization") String authHeader);
}
