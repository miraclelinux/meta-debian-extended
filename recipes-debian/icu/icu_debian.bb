require ${COREBASE}/meta/recipes-support/icu/icu.inc

inherit debian-package
require recipes-debian/sources/icu.inc
FILESEXTRAPATHS_prepend := "${THISDIR}/icu:"

LIC_FILES_CHKSUM = "file://LICENSE;md5=63752c57bd0b365c9af9f427ef79c819"

def icu_download_version(d):
    pvsplit = d.getVar('PV').split('.')
    return pvsplit[0] + "_" + pvsplit[1]

ICU_PV = "${@icu_download_version(d)}"

# http://errors.yoctoproject.org/Errors/Details/20486/
ARM_INSTRUCTION_SET_armv4 = "arm"
ARM_INSTRUCTION_SET_armv5 = "arm"

SRC_URI += " \
    file://0001-icu-fix-install-race.patch \
    file://0002-icu-Add-ARC-support.patch \
        "

UPSTREAM_CHECK_REGEX = "(?P<pver>\d+(\.\d+)+)/"
UPSTREAM_CHECK_URI = "http://download.icu-project.org/files/icu4c/"

AUTOTOOLS_SCRIPT_PATH = "${S}/source"
