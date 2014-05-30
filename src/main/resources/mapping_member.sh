curl -XPOST localhost:9200/member -d '{
    "settings" : {
        "number_of_shards" : 16,
        "number_of_replicas" : 0
    },
    "mappings" : {
        "status" : {
            "_source" : {
                "enabled" : true,
                "compress" : true
            },
            "properties" : {
                "balances" : {
                    "type" : "nested",
                    "properties" : {
                        "cachedBalance" : {"type":"double"},
                        "newMemberID" : {"type":"long"}
                    }
                },
                "birthYear" : {"type":"string", "index":"not_analyzed"},
                "birthYearNum" : {"type":"long"},
                "birthDecade" : {"type":"string", "index":"not_analyzed"},
                "age" : {"type":"long"},
                "claims" : {
                    "type" : "nested",
                    "properties" : {
                        "claimDetails" : {
                            "properties" : {
                                "cptCode" : {"type":"string", "index":"not_analyzed"},
                                "newClaimID" : {"type":"long"}
                            }
                        },
                        "claimType" : {"type":"string", "index":"not_analyzed"},
                        "cptCodesAll" : {"type":"string", "index":"not_analyzed"},
                        "cptCodesUnique" : {"type":"string", "index":"not_analyzed"},
                        "dateProcessed" : {"type":"long"},
                        "dateReceived" : {"type":"long"},
                        "dependentServiced" : {"type":"long"},
                        "newClaimID" : {"type":"long"},
                        "newMemberID" : {"type":"long"},
                        "patientResponsibilityAmount" : {"type":"double"},
                        "repricedAmount" : {"type":"double"},
                        "serviceEnd" : {"type":"long"},
                        "serviceStart" : {"type":"long"}
                    }
                },
                "contributionsAndPayments" : {
                    "type" : "nested",
                    "properties" : {
                        "amount" : {"type":"double"},
                        "category" : {"type":"string", "index":"not_analyzed"},
                        "newMemberID" : {"type":"long"},
                        "paymentAvailableDate" : {"type":"long"}
                    }
                },
                "memberContributions" : {
                    "type" : "nested",
                    "properties" : {
                        "amount" : {"type":"double"},
                        "category" : {"type":"string", "index":"not_analyzed"},
                        "newMemberID" : {"type":"long"},
                        "paymentAvailableDate" : {"type":"long"}
                    }
                },
                "memberPayments" : {
                    "type" : "nested",
                    "properties" : {
                        "amount" : {"type":"double"},
                        "category" : {"type":"string", "index":"not_analyzed"},
                        "newMemberID" : {"type":"long"},
                        "paymentAvailableDate" : {"type":"long"}
                    }
                },
                "companyContributions" : {
                    "type" : "nested",
                    "properties" : {
                        "amount" : {"type":"double"},
                        "category" : {"type":"string", "index":"not_analyzed"},
                        "newMemberID" : {"type":"long"},
                        "paymentAvailableDate" : {"type":"long"}
                    }
                },
                "cptCodesAll" : {"type":"string", "index":"not_analyzed"},
                "cptCodesUnique" : {"type":"string", "index":"not_analyzed"},
                "dependents" : {
                    "type" : "nested",
                    "properties" : {
                        "birthYear" : {"type":"string", "index":"not_analyzed"},
                        "birthDecade" : {"type":"string", "index":"not_analyzed"},
                        "age" : {"type":"string", "index":"not_analyzed"},
                        "dependentID" : {"type":"long"},
                        "gender" : {"type":"string", "index":"not_analyzed"},
                        "newMemberID" : {"type":"long"},
                        "relationship" : {"type":"string", "index":"not_analyzed"},
                        "state" : {"type":"string", "index":"not_analyzed"},
                        "zip" : {"type":"string", "index":"not_analyzed"}
                      }
                },
                "gender" : {"type":"string", "index":"not_analyzed"},
                "hsaEffectiveDate" : {"type":"long"},
                "newMemberID" : {"type":"long"},
                "numberOfBalances" : {"type":"long"},
                "numberOfClaims" : {"type":"long"},
                "numberOfContributionsAndPayments" : {"type":"long"},
                "numberOfDependents" : {"type":"long"},
                "numberOfEmployeeContributions" : {"type":"long"},
                "numberOfEmployerContributions" : {"type":"long"},
                "numberofPayments" : {"type":"long"},
                "state" : {"type":"string", "index":"not_analyzed"},
                "totalClaimsPatientResponsibilityAmount" : {"type":"long"},
                "totalClaimsRepricedAmount" : {"type":"double"},
                "totalContributionsAndPayments" : {"type":"double"},
                "totalEmployeeContributions" : {"type":"double"},
                "totalEmployerContributions" : {"type":"double"},
                "totalOfBalances" : {"type":"double"},
                "totalPayments" : {"type":"double"},
                "zip" : {"type":"string", "index":"not_analyzed"}
            }
        }
    }
}'
