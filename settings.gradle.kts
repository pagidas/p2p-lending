rootProject.name = "zopa-tech-test"

include(
    "quote",
    "quote:domain",
    "quote:csv-parsing-adapter",
    "util"
)
include("quote:console-out-adapter")
findProject(":quote:console-out-adapter")?.name = "console-out-adapter"
include("app")
