FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR = "r1"

SRC_URI += "file://CVE-2022-48174.patch"
