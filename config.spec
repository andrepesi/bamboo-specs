{
  "Name" : "AndrePerairaSilva",
  "Key" : "KYANDR314",
  "OID" : "3ffca5763116",
  "Repository" : "andre@git.com",
  "RepositoryUrl" : null,
  "DefaultBranch" : "master",
  "PlanBranchManagement" : "BranchMatching",
  "Plan" : {
    "Name" : "PLANDR770",
    "Key" : "KYPLAN343",
    "Description" : "",
    "Stages" : [ {
      "Name" : "Build",
      "Jobs" : [ {
        "Name" : "Default Job",
        "Key" : "JBANDR018",
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
          "TaskType" : "NetFramework",
          "Properties" : {
            "args" : "",
            "projectPath" : "andre",
            "version" : ""
          }
        }, {
          "Order" : 4,
          "TaskType" : "Nuget",
          "Properties" : {
            "nugetToken" : "${bamboo.NUGET_API_TOKEN_PASSWORD}",
            "projectPath" : "src/ConsoleApp",
            "nugetPackagePath" : null,
            "nugetServer" : "${bamboo.NUGET_TEST_SERVER}",
            "version" : "${bamboo.GitVersion.AssemblySemVer}"
          }
        }, {
          "Order" : 4,
          "TaskType" : "Nuget",
          "Properties" : {
            "nugetToken" : "${bamboo.NUGET_API_TOKEN_PASSWORD}",
            "projectPath" : "src/ConsoleApp",
            "nugetPackagePath" : null,
            "nugetServer" : "${bamboo.NUGET_TEST_SERVER}",
            "version" : "${bamboo.GitVersion.AssemblySemVer}"
          }
        }, {
          "Order" : 4,
          "TaskType" : "Nuget",
          "Properties" : {
            "nugetToken" : "${bamboo.NUGET_API_TOKEN_PASSWORD}",
            "projectPath" : "src/ConsoleApp",
            "nugetPackagePath" : null,
            "nugetServer" : "${bamboo.NUGET_TEST_SERVER}",
            "version" : "${bamboo.GitVersion.AssemblySemVer}"
          }
        } ]
      } ]
    } ]
  }
}