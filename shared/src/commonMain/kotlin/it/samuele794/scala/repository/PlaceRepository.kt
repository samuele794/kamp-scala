package it.samuele794.scala.repository

import it.samuele794.scala.model.maps.LatLng
import it.samuele794.scala.model.maps.Place
import it.samuele794.scala.model.maps.getGeoHashQueryBounds
import kotlinx.coroutines.coroutineScope

interface PlaceRepository {

    suspend fun getPlace(documentReference: String): Place

    suspend fun getPlacesByBounds(center: LatLng, rangeInM: Double = 10.0, limit: Int = 10): List<Place>

    suspend fun addPlace(place: Place)
}

class PlaceRepositoryImpl : PlaceRepository {

    override suspend fun getPlace(documentReference: String): Place {
        TODO()
//        return Firebase.firestore.document(documentReference)
//            .get()
//            .data(Place.serializer())
    }

    override suspend fun addPlace(place: Place) {
        TODO()
//        val doc = Firebase.firestore.collection(PLACE_COLLECTION)
//            .document(place.geoHash)
//        doc.set(Place.serializer(), place)
//        return doc
    }

    override suspend fun getPlacesByBounds(center: LatLng, rangeInM: Double, limit: Int): List<Place> =
        coroutineScope {
            TODO()
            val bounds = getGeoHashQueryBounds(center, rangeInM)

//            val query = mutableListOf<Deferred<QuerySnapshot>>()

//            bounds.forEach {
//                query.add(
//                    async {
//                        Firebase.firestore.collection(PLACE_COLLECTION)
//                            .orderBy(Place::geoHash.name)
//                            .startAt(it.startHash)
//                            .endAt(it.endHash)
//                            .get()
//                    }
//                )
//            }

//            val result = query.awaitAll()

            val matchingDocs = mutableListOf<Place>()

//            result.forEach {
//                it.documents.forEach { doc ->
//                    val distanceInM = getDistanceBetween(doc.get(Place::latLng.name), center)
//                    if (distanceInM <= rangeInM) {
//                        matchingDocs.add(doc.data(Place.serializer()))
//                    }
//                }
//            }

            return@coroutineScope matchingDocs

        }

    companion object {
        const val PLACE_COLLECTION = "places"
    }
}