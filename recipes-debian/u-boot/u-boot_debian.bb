# base recipe: meta/recipes-bsp/u-boot/u-boot_2019.01.bb
# base branch: warrior
# base commit: 2d74ea28010712547f81d200c2957e05779ef3bd

require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot-common.inc
require ${COREBASE}/meta/recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bc-native dtc-native"

inherit debian-package
require recipes-debian/sources/u-boot.inc
