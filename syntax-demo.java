// 1 dist, 1 exe build in the package
ReleaseTemplate.release("community") {
	distribution("standard").from {
		exe = Launch4jExeTemplate.exe {
			from = "/release/standard/exe"
		}
		companionFiles = "/release/standard/companion"
	}
}

// or could we do dist ?
ReleaseTemplate.release("community") {
	dist {
		exe = Launch4jExeTemplate.exe {
			from = "/release/standard/exe"
		}
		companionFiles = "/release/standard/companion"
	}
}

// or with a distribution template, could be shortened still more
ReleaseTemplate.release("community") {
	distribution("standard").from {
		template = Launch4jDistributionTemplate.dist {
			resourcesDir = "/release/standard"
		}
	}
}

// or better yet?
ReleaseTemplate.release("community") {
	dist(Launch4jDistributionTemplate){
		resourcesDir = "/release/community/gui"
	}
}

// or better yet?
ReleaseTemplate.release("community") {
	dist(Launch4jDistributionTemplate){
		resourcesDir = "/release/community/gui"
	}
}

// accessors
releases.community.distributions.community.resourcesDir
releases.community.distributions.community.exeTask
releases.community.distributions.community.distributionBuildTask
releases.community.distributions.community.distributionAssembleTask

// 1 dist, 2 exe builds in the package
// N.B. would require that the names of the outputs from the exe tasks MUST be different!
ReleaseTemplate.release("community") {
	dist {
		template = Launch4jDistributionTemplate.dist {
			exe("gui") {
				resourcesDir = "/release/community/gui"
				custom {
					headerType = "gui"
				}
			}
			exe("console") {
				resourcesDir = "/release/community/console"
				custom {
					headerType = "console"
				}
			}
		}
	}
}

// or this!  We could define some kind of an interface
// that would require either a no-args constructor
// or a constructor(Project).
ReleaseTemplate.release("community") {
	dist(Launch4jDistributionTemplate){
		exe("gui") {
			resourcesDir = "/release/community/gui"
			custom {
				headerType = "gui"
			}
		}
		exe("console") {
			resourcesDir = "/release/community/console"
			custom {
				headerType = "console"
			}
		}
	}
}


// 2 dist, 1 exe in each package
ReleaseTemplate.release("allEditions") {
	distribution("community").from {
		template = Launch4jDistributionTemplate.dist {
			resourcesDir = "/editions/community"
		}
	}
	distribution("ultimate").from {
		template = Launch4jDistributionTemplate.dist {
			resourcesDir = "/editions/ultimate"
		}
	}
}


// 2 dist, 2 exe builds in each package
ReleaseTemplate.release("community") {
	dist("community", Launch4jDistributionTemplate){
		exe("gui") {
			resourcesDir = "/release/community/gui"  // copies exe build info and companions
			custom {
				headerType = "gui"
			}
		}
		exe("console") {
			resourcesDir = "/release/community/console"
			custom {
				headerType = "console"
			}
		}
	}
	dist("ultimate", Launch4jDistributionTemplate){
		exe("gui") {
			resourcesDir = "/release/ultimate/gui"
			custom {
				headerType = "gui"
			}
		}
		exe("console") {
			resourcesDir = "/release/ultimate/console"
			custom {
				headerType = "console"
			}
		}
	}
}

