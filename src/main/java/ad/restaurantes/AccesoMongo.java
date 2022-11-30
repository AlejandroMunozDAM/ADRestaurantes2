package ad.restaurantes;

import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccesoMongo implements AccesoRestaurantes {

    private final MongoClient mongoClient;
    private final MongoCollection<Document> col;

    public AccesoMongo() {
        Logger.getLogger("org.mongodb").setLevel(Level.WARNING);
        mongoClient = MongoClients.create();
        col = mongoClient.getDatabase("test").getCollection("rest");
    }

    @Override
    public List<String> getTiposCocina() {
        DistinctIterable<String> cuisines = col.distinct("cuisine", String.class);
        List<String> tiposCocina = new ArrayList<>();
        for (String cuisine : cuisines) {
            tiposCocina.add(cuisine);
        }
        return tiposCocina;
    }

    @Override
    public List<Restaurante> getRestaurantes(String tipoCocinaElegido, int cantidadRestaurantes) {
        /* db.rest.find({cuisine:"Spanish"},
        {_id:false,name:true,borough:true,cuisine:true,street:"$address.street",
        zipcode:"$address.zipcode"}).limit(3)
         */
        Bson filtro = Filters.eq("cuisine", tipoCocinaElegido);
        Bson proyeccion = Filters.and(
                new Document("_id", false),
                new Document("name", true),
                new Document("cuisine", true),
                new Document("borough", true),
                new Document("street", "$address.street"),
                new Document("zipcode", "$address.zipcode")
        );
        FindIterable<Document> restEncontrados = col.find(filtro).projection(proyeccion).limit(cantidadRestaurantes);
        List<Restaurante> listaRestaurantes = new ArrayList<>();
        for (Document docRest : restEncontrados) {
            Restaurante restaurante = new Restaurante(
                    docRest.getString("name"),
                    docRest.getString("street"),
                    docRest.getString("zipcode"),
                    docRest.getString("cuisine"),
                    docRest.getString("borough")
            );
            listaRestaurantes.add(restaurante);
        }
        return listaRestaurantes;
    }

    @Override
    public void alta(List<Restaurante> restaurantes) {

    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }
}
