{
  "Name" : "BambooBrownBag",
  "Key" : "KYBAMB074",
  "Repository" : "playground",
  "RepositoryUrl" : "https://andre_silva@git.srs.nuance.com/scm/eng/playground.git",
  "DefaultBranch" : "master",
  "PlanBranchManagement" : "BranchMatching",
  "Plan" : {
    "Name" : "PLBAMB844",
    "Key" : "KYPLBA235",
    "Description" : "",
    "Stages" : [ {
      "Name" : "Build",
      "Jobs" : [ {
        "Name" : "Default Job",
        "Key" : "JBBAMB535",
        "Tasks" : [ {
          "Order" : 0,
          "TaskType" : "RepositoryCheckout",
          "Properties" : {
            "repository" : ""
          }
        }, {
          "Order" : 1,
          "TaskType" : "GitVersion",
          "Properties" : {
            "args" : "/UpdateAssemblyInfo",
            "savedVars" : "",
            "repoPath" : ""
          }
        }, {
          "Order" : 3,
          "TaskType" : "NetCore",
          "Properties" : {
            "args" : "",
            "configuration" : "${bamboo.GitVersion.AssemblySemVer}",
            "projectPath" : "src/BambooBrownBag",
            "version" : "${bamboo.GitVersion.AssemblySemVer}"
          }
        } ]
      } ]
    } ]
  }
}