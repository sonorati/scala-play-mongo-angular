
class PlayerService

    @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
    @defaultConfig = { headers: @headers }

    constructor: (@$log, @$http, @$q) ->
        @$log.debug "constructing PlayerService"

    listPlayers: () ->
        @$log.debug "listPlayers()"
        deferred = @$q.defer()

        @$http.get("/players")
        .success((data, status, headers) =>
                @$log.info("Successfully listed Players - status #{status}")
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
                @$log.error("Failed to list Players - status #{status}")
                deferred.reject(data);
            )
        deferred.promise

    createPlayer: (player) ->
        @$log.debug "createPlayer #{angular.toJson(player, true)}"
        deferred = @$q.defer()

        @$http.post('/player', player)
        .success((data, status, headers) =>
                @$log.info("Successfully created Player - status #{status}")
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
                @$log.error("Failed to create player - status #{status}")
                deferred.reject(data);
            )
        deferred.promise

servicesModule.service('PlayerService', PlayerService)
