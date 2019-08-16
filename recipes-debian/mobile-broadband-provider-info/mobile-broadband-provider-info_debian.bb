# base recipe: meta/recipes-connectivity/mobile-broadband-provider-info/mobile-broadband-provider-info_git.bb
# base branch: warrior
# base commit: 6bb442b3f8e6e0eeedf1ecd6dab286ca08f18ac9

SUMMARY = "Mobile Broadband Service Provider Database"
HOMEPAGE = "http://live.gnome.org/NetworkManager/MobileBroadband/ServiceProviders"
SECTION = "network"
LICENSE = "PD"
LIC_FILES_CHKSUM = "file://COPYING;md5=87964579b2a8ece4bc6744d2dc9a8b04"
PE = "1"

inherit autotools debian-package
require recipes-debian/sources/mobile-broadband-provider-info.inc
DEBIAN_QUILT_PATCHES = ""
