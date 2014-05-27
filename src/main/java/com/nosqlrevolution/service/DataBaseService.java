package com.nosqlrevolution.service;

import com.nosqlrevolution.model.data.Balance;
import com.nosqlrevolution.model.data.Claim;
import com.nosqlrevolution.model.data.ClaimDetail;
import com.nosqlrevolution.model.data.ContributionsAndPayments;
import com.nosqlrevolution.model.data.Dependent;
import com.nosqlrevolution.model.data.Member;
import com.nosqlrevolution.waltermitty.App;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DataBaseService
{
    private Connection connect = null;

    public DataBaseService()
    {
        connectToDatabase();
    }

    // Connect to database
    public void connectToDatabase()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // setup the connection with the DB.

            if (App.MYSQL_CONNECT_STRING != null && !App.MYSQL_CONNECT_STRING.isEmpty())
            {
                connect = DriverManager.getConnection(App.MYSQL_CONNECT_STRING);
            }
            else
            {
                System.out.println("Error: App.MYSQL_CONNECT_STRING is not configured");
            }
        } 
       catch (Exception ex)
        {
            // handle the error
            ex.printStackTrace();
        }
    }

    public ArrayList<Member> getMembers()
    {
        Statement statement = null;
        ArrayList<Member> members = null;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar startTime = Calendar.getInstance();

        if (connect != null)
        {
            try {
                statement = connect.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from data.member");

                members = new ArrayList<Member>();
                while (resultSet.next())
                {
                    Member member = new Member();
                    member.setNewMemberID(resultSet.getInt("NewMemberID"));

                    // show progress via "Progress Indicator" output
                    if (members.size() > 0)
                    {
                        if ((members.size() % 100) == 0)
                        {
                            System.out.print(".");
                        }

                        if ( (members.size() % 1000) == 0)
                        {
                            long diff = Calendar.getInstance().getTimeInMillis() - startTime.getTimeInMillis();
                            System.out.println("processed: " + members.size() + " members :  Total Time processing: " + String.format("%d min, %d sec",
                                    TimeUnit.MILLISECONDS.toMinutes(diff),
                                    TimeUnit.MILLISECONDS.toSeconds(diff) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(diff))
                            ));
                        }
                    }

                    member.setState(resultSet.getString("State"));
                    member.setZip(trimZip(resultSet.getString("Zip")));
                    member.setGender(resultSet.getString("Gender"));
                    member.setBirthYear(resultSet.getString("BirthYear"));
                    member.setBirthDecade(getBirthDecade(member.getBirthYear()));
                    member.setAge(getAge(member.getBirthYear()));
                    member.setHsaEffectiveDate(resultSet.getTimestamp("HsaEffectiveDate"));

                    member.setDependents(getDependents(member.getNewMemberID()));
                    member.setBalances(getBalances(member.getNewMemberID()));
                    member.setClaims(getClaims(member.getNewMemberID()));
                    if (member.getClaims() != null)
                    {
                        for (Claim claim: member.getClaims())
                        {
                            member.addCptCodesAll(claim.getCptCodesAll());
                            member.addCptCodesUnique(claim.getCptCodesUnique());
                        }
                    }

                    
                    member.setContributionsAndPayments(getContributionsAndPayments(member.getNewMemberID()));
                    member.setMemberContributions(getMemberContributions(member.getContributionsAndPayments()));
                    member.setMemberPayments(getMemberPayments(member.getContributionsAndPayments()));
                    member.setCompanyContributions(getCompanyContributions(member.getContributionsAndPayments()));

                    members.add(member);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statement != null)
                    {
                        statement.close();
                    }
                    if (connect != null)
                    {
                        connect.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return members;
    }

    public ArrayList<Dependent> getDependents(int newMemberID)
    {
        Statement statementDependent = null;
        ArrayList<Dependent> dependents = null;

        if (connect != null) {
            try {
                statementDependent = connect.createStatement();
                ResultSet resultSetDependent = statementDependent.executeQuery("select * from data.dependent where NewMemberID = " + newMemberID);

                while (resultSetDependent.next()) {
                    if (dependents == null) {
                        dependents = new ArrayList<Dependent>();
                    }
                    Dependent dependent = new Dependent();
                    dependent.setNewMemberID(resultSetDependent.getInt("NewMemberID"));
                    dependent.setDependentID(resultSetDependent.getInt("DependentID"));
                    dependent.setRelationship(resultSetDependent.getString("Relationship"));
                    dependent.setBirthYear(resultSetDependent.getString("BirthYear"));
                    dependent.setBirthDecade(getBirthDecade(dependent.getBirthYear()));
                    dependent.setAge(getAge(dependent.getBirthYear()));
                    dependent.setGender(resultSetDependent.getString("Gender"));
                    dependent.setState(resultSetDependent.getString("State"));
                    dependent.setZip(trimZip(resultSetDependent.getString("Zip")));

                    dependents.add(dependent);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (statementDependent != null) {
                        statementDependent.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return dependents;
    }

    private ArrayList<Claim> getClaims(int newMemberID)
    {
        Statement statementClaim = null;
        ArrayList<Claim> claims = null;

        if (connect != null)
        {
            try {
                statementClaim = connect.createStatement();
                ResultSet resultSetClaim = statementClaim.executeQuery("select * from data.claim where NewMemberID = " + newMemberID);

                while (resultSetClaim.next()) {
                    if (claims == null)
                    {
                        claims = new ArrayList<Claim>();
                    }
                    Claim claim = new Claim();
                    claim.setNewClaimID(resultSetClaim.getInt("NewClaimID"));
                    claim.setNewMemberID(resultSetClaim.getInt("NewMemberID"));
                    claim.setDependentServiced(resultSetClaim.getInt("DependentServiced"));
                    claim.setClaimType(resultSetClaim.getString("ClaimType"));
                    claim.setDateReceived(resultSetClaim.getTimestamp("DateReceived"));
                    claim.setDateProcessed(resultSetClaim.getTimestamp("DateProcessed"));
                    claim.setServiceStart(resultSetClaim.getTimestamp("ServiceStart"));
                    claim.setServiceEnd(resultSetClaim.getTimestamp("ServiceEnd"));
                    claim.setRepricedAmount(resultSetClaim.getFloat("RepricedAmount"));
                    claim.setPatientResponsibilityAmount(resultSetClaim.getFloat("PatientResponsibilityAmount"));

                    claim.setClaimDetails(getClaimDetails(claim.getNewClaimID()));
                    if (claim.getClaimDetails() != null)
                    {
                        List<String> cptCodesAll = new ArrayList<String>();
                        Set<String> cptCodesUnique = new HashSet<String>();

                        for (ClaimDetail detail: claim.getClaimDetails())
                        {
                            cptCodesAll.add(detail.getCptCode());
                            cptCodesUnique.add(detail.getCptCode());
                        }

                        claim.addCptCodesAll(cptCodesAll);
                        claim.addCptCodesUnique(cptCodesUnique);
                    }

                    claims.add(claim);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statementClaim != null)
                    {
                        statementClaim.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return claims;
    }

    private ArrayList<Balance> getBalances(int newMemberID)
    {
        Statement statementBalance = null;
        ArrayList<Balance> balances = null;

        if (connect != null)
        {
            try {
                statementBalance = connect.createStatement();
                ResultSet resultSetBalance = statementBalance.executeQuery("select * from data.balance where NewMemberID = " + newMemberID);

                while (resultSetBalance.next()) {
                    if (balances == null)
                    {
                        balances = new ArrayList<Balance>();
                    }
                    Balance balance = new Balance();
                    balance.setNewMemberID(resultSetBalance.getInt("NewMemberID"));
                    balance.setCachedBalance(resultSetBalance.getFloat("CachedBalance"));

                    balances.add(balance);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statementBalance != null)
                    {
                        statementBalance.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return balances;
    }

    private ArrayList<ClaimDetail> getClaimDetails(int newClaimID)
    {
        Statement statementClaimDetail = null;
        ArrayList<ClaimDetail> claimDetails = null;

        if (connect != null)
        {
            try {
                statementClaimDetail = connect.createStatement();
                ResultSet resultSetClaimDetail = statementClaimDetail.executeQuery("select * from data.claimdetail where NewClaimID = " + newClaimID);

                while (resultSetClaimDetail.next()) {
                    if (claimDetails == null)
                    {
                        claimDetails = new ArrayList<ClaimDetail>();
                    }
                    ClaimDetail claimDetail = new ClaimDetail();
                    claimDetail.setNewClaimID(resultSetClaimDetail.getInt("NewClaimID"));
                    claimDetail.setCptCode(resultSetClaimDetail.getString("CPTCode"));

                    claimDetails.add(claimDetail);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statementClaimDetail != null)
                    {
                        statementClaimDetail.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return claimDetails;
    }


    private ArrayList<ContributionsAndPayments> getContributionsAndPayments(int newMemberID)
    {
        Statement statementContributions = null;
        ArrayList<ContributionsAndPayments> contributionsAndPaymentsList = null;

        if (connect != null)
        {
            try {
                statementContributions = connect.createStatement();
                ResultSet resultSetContributions = statementContributions.executeQuery("select * from data.contributionandpayment where NewMemberID = " + newMemberID);

                while (resultSetContributions.next()) {
                    if (contributionsAndPaymentsList == null)
                    {
                        contributionsAndPaymentsList = new ArrayList<ContributionsAndPayments>();
                    }
                    ContributionsAndPayments contributionsAndPayments = new ContributionsAndPayments();
                    contributionsAndPayments.setNewMemberID(resultSetContributions.getInt("NewMemberID"));
                    contributionsAndPayments.setAmount(resultSetContributions.getFloat("Amount"));
                    contributionsAndPayments.setCategory(resultSetContributions.getString("Category"));
                    contributionsAndPayments.setPaymentAvailableDate(resultSetContributions.getTimestamp("PaymentAvailableDate"));

                    contributionsAndPaymentsList.add(contributionsAndPayments);
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (statementContributions != null)
                    {
                        statementContributions.close();
                    }
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return contributionsAndPaymentsList;
    }
    
    private String getBirthDecade(String birthYear) {
        if (birthYear == null || birthYear.isEmpty()) { return null; }
        return (birthYear.substring(0, 2) + "0");
    }
    
    private String getAge(String birthYear) {
        try {
            int birth = Integer.parseInt(birthYear);
            return Integer.toString(2014 - birth);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private String trimZip(String zip) {
        if (zip == null || zip.isEmpty() || zip.length() == 5) { return zip; }
        return zip.substring(0,4);
    }

    private ArrayList<ContributionsAndPayments> getMemberContributions(ArrayList<ContributionsAndPayments> contributions) {
        if (contributions == null || contributions.isEmpty()) { return null; }
        
        ArrayList<ContributionsAndPayments> out = new ArrayList<ContributionsAndPayments>();
        for (ContributionsAndPayments contrib: contributions) {
            if (contrib.getCategory().equalsIgnoreCase("ContEmployee")) {
                out.add(contrib);
            }
        }
        
        return ! out.isEmpty() ? out : null;
    }

    private ArrayList<ContributionsAndPayments> getMemberPayments(ArrayList<ContributionsAndPayments> contributions) {
        if (contributions == null || contributions.isEmpty()) { return null; }
        
        ArrayList<ContributionsAndPayments> out = new ArrayList<ContributionsAndPayments>();
        for (ContributionsAndPayments contrib: contributions) {
            if (contrib.getCategory().equalsIgnoreCase("DistNormal")) {
                out.add(contrib);
            }
        }
        
        return ! out.isEmpty() ? out : null;
    }

    private ArrayList<ContributionsAndPayments> getCompanyContributions(ArrayList<ContributionsAndPayments> contributions) {
        if (contributions == null || contributions.isEmpty()) { return null; }
        
        ArrayList<ContributionsAndPayments> out = new ArrayList<ContributionsAndPayments>();
        for (ContributionsAndPayments contrib: contributions) {
            if (contrib.getCategory().equalsIgnoreCase("ContEmployer")) {
                out.add(contrib);
            }
        }
        
        return ! out.isEmpty() ? out : null;
    }
}
