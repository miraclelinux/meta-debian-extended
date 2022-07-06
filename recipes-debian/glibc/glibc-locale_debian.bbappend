# Building glibc-locale package sometimes get host-user-contaminated warning. This warning is not always reported.
# This issue is discussed on openembedded-core[1] and a work-around has been proposed[2] for older branches although the work-around patch has not been applied yet.
# We take the approach of [2], that is to set owner and group to root for files in $treedir.
# 1. https://lore.kernel.org/openembedded-core/20220616055414.42319-1-muhammad_hamza@mentor.com/T/#t
# 2. https://patches.linaro.org/project/oe-core/patch/20190207003537.7135-1-raj.khem@gmail.com/
do_prep_locale_tree_append () {
	chown -R root:root $treedir
}
