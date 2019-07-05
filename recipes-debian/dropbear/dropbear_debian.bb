# base recipe: meta/recipes-core/dropbear/dropbear_2019.78.bb
# base branch: warrior
# base commit: fa29e17411fb5847a70892bcc264ba602da96239

require ${COREBASE}/meta/recipes-core/dropbear/dropbear.inc

inherit debian-package
require recipes-debian/sources/${BPN}.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-core/${PN}/${PN}"
SRC_URI += "\
	file://0001-urandom-xauth-changes-to-options.h.patch \
	file://init \
	file://dropbearkey.service \
	file://dropbear@.service \
	file://dropbear.socket \
	file://dropbear.default \
	${@bb.utils.contains('DISTRO_FEATURES', 'pam', '${PAM_SRC_URI}', '', d)} \
"
