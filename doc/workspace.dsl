workspace "Spring Cloud Stream: Example" {

    model {
        u = person "User"

        askme = softwareSystem "Ask me!" "To answer user's questions" {
            g = container GatewayService "The user interface" "Java app"
            s = container DistributorService "To dispatch tiem/date questions" "Java app"
            t = container TimeService "To nswers time questions" "Java app"
            d = container DateService "To answer date questions" "Java app"

            g -> s "asks" "via 'questions' stream"
            s -> g "answers any invalid question" "via 'answers' stream"
            s -> t "send time questions" "via 'time-questions' stream"
            t -> g "answers time questions" "via 'answers' stream"
            s -> d "send date questions" "via 'date-questions' stream"
            d -> g "answers date questions" "via 'answers' stream"
        }

        u -> g "asks questions" "REST api"
    }

    views {
        styles {
            theme default
        }

        systemlandscape "SystemLandscape" {
            include *
            autolayout
        }

        systemContext askme {
            include *
            autolayout
        }

        container askme {
            include *
            autolayout
        }
    }

}