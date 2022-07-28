rootProject.name = "p2p-lending"

include(
    "quote",
    "quote:domain",
    "quote:csv-parsing-adapter",
    "util"
)
include("quote:console-out-adapter")
findProject(":quote:console-out-adapter")?.name = "console-out-adapter"
include("app")
