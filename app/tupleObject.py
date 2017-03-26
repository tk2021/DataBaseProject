class tupleObject:
	attributes = []
	x = 0

	def __init__(self, attributeList):
		attributes = attributeList
		x = 9

	def __iter__(self):
		return iter(self.attributes)