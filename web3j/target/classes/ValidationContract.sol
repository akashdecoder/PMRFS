// SPDX-License-Identifier: GPL-3.0

pragma solidity ^0.4.0;

import "./Strings.sol";

contract PmCare {

    function Patient_Fund_Validation(uint256 fundNeed, string casetype, bool hospitalRequestFlag) public pure returns(bool){
        if(hospitalRequestFlag == true){
            if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Cancer"))) {
                if(fundNeed >13){
                    return false;
                }else{
                    return true;
                }
            } else if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Neuro"))) {
                if(fundNeed >12){
                    return false;
                }else{
                    return true;
                }
            } else if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Heart"))) {
                if(fundNeed >16){
                    return false;
                }else{
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    function Public_Service_Fund_Validation(uint256 fundNeed, string casetype, bool validRequestFlag) public pure returns(bool){
        if(validRequestFlag == true){
            if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Public Laboratories"))){
                if(fundNeed >178){
                    return false;
                }else{
                    return true;
                }
            } else if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Hospital&Dispensaries"))){
                if(fundNeed >456){
                    return false;
                }else{
                    return true;
                }
            } else if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Educational Institutes"))) {
                if(fundNeed >243){
                    return false;
                }else{
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    function Victim_Fund_Validation(uint256 fundNeed, string casetype, bool validRequestFlag) public pure returns(bool){
        if(validRequestFlag == true){
            if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Accident"))){
                if(fundNeed >25){
                    return false;
                }else{
                    return true;
                }
            } else if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Flood Damage"))){
                if(fundNeed >32){
                    return false;
                }else{
                    return true;
                }
            } else if(keccak256(abi.encodePacked(casetype)) == keccak256(abi.encodePacked("Earthquake Damage"))) {
                if(fundNeed >40){
                    return false;
                }else{
                    return true;
                }
            } else {
                return false;
            }
        }
        return false;
    }
}
