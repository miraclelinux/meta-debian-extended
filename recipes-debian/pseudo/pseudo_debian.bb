# base recipe: meta/recipes-devtools/pseudo/pseudo_git.bb
# base branch: warrior
# base commit: cef43d10bdadb3d492293177d8e6edf9cf313d29

require ${COREBASE}/meta/recipes-devtools/pseudo/pseudo.inc

inherit debian-package
require recipes-debian/sources/pseudo.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/pseudo/files"

SRC_URI += " \
           file://0001-configure-Prune-PIE-flags.patch \
           file://fallback-passwd \
           file://fallback-group \
           file://moreretries.patch \
           file://toomanyfiles.patch \
           "
