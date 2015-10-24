class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}

		"/"(action: "index",controller: 'state')
		"500"(view:'/error')
	}
}
